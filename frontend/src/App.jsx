import React, { useState, useEffect } from 'react';
import { 
  ArrowLeftRight, 
  Scale, 
  Plus, 
  Minus, 
  Divide, 
  History, 
  CheckCircle, 
  AlertCircle, 
  RefreshCw, 
  Activity, 
  Layers, 
  Thermometer, 
  Database,
  Filter,
  Trash2
} from 'lucide-react';

const UNITS = {
  LENGTH: [
    { value: 'FEET', label: 'Feet (ft)' },
    { value: 'INCH', label: 'Inch (in)' },
    { value: 'YARDS', label: 'Yards (yd)' },
    { value: 'CENTIMETERS', label: 'Centimeters (cm)' },
    { value: 'CM', label: 'CM (cm)' }
  ],
  WEIGHT: [
    { value: 'KILOGRAM', label: 'Kilogram (kg)' },
    { value: 'GRAM', label: 'Gram (g)' },
    { value: 'TONNE', label: 'Tonne (t)' }
  ],
  VOLUME: [
    { value: 'LITRE', label: 'Litre (L)' },
    { value: 'MILLILITRE', label: 'Millilitre (mL)' },
    { value: 'GALLON', label: 'Gallon (gal)' }
  ],
  TEMPERATURE: [
    { value: 'CELSIUS', label: 'Celsius (°C)' },
    { value: 'FAHRENHEIT', label: 'Fahrenheit (°F)' }
  ]
};

const DEFAULT_UNITS = {
  LENGTH: { first: 'FEET', second: 'INCH', target: 'FEET' },
  WEIGHT: { first: 'KILOGRAM', second: 'GRAM', target: 'KILOGRAM' },
  VOLUME: { first: 'LITRE', second: 'MILLILITRE', target: 'LITRE' },
  TEMPERATURE: { first: 'CELSIUS', second: 'FAHRENHEIT', target: 'CELSIUS' }
};

// Helper to decode JWT payload safely in pure JS
const decodeToken = (token) => {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
  } catch (e) {
    return null;
  }
};

export default function App() {
  // Authentication & Session States
  const [token, setToken] = useState(localStorage.getItem('token') || '');
  const [user, setUser] = useState(null);

  // Tabs & Navigation State
  const [activeTab, setActiveTab] = useState('convert'); // convert, compare, add, subtract, divide
  const [measurementType, setMeasurementType] = useState('LENGTH'); // LENGTH, WEIGHT, VOLUME, TEMPERATURE
  
  // Input Values State
  const [val1, setVal1] = useState(1.0);
  const [unit1, setUnit1] = useState(DEFAULT_UNITS.LENGTH.first);
  const [val2, setVal2] = useState(12.0);
  const [unit2, setUnit2] = useState(DEFAULT_UNITS.LENGTH.second);
  const [targetUnit, setTargetUnit] = useState(DEFAULT_UNITS.LENGTH.target);

  // Connection & API States
  const [connected, setConnected] = useState(null); // null (checking), true, false
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState(null);
  
  // Database History States
  const [history, setHistory] = useState([]);
  const [historyLoading, setHistoryLoading] = useState(false);
  const [filterOp, setFilterOp] = useState('ALL');
  const [filterErrored, setFilterErrored] = useState(false);

  // Operation Metrics State
  const [metrics, setMetrics] = useState({
    compare: 0,
    convert: 0,
    add: 0,
    subtract: 0,
    divide: 0
  });

  // Dynamic Bearer Token Headers
  const fetchHeaders = {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  };

  // Decode active token on mount or update
  useEffect(() => {
    if (token) {
      const claims = decodeToken(token);
      if (claims) {
        setUser(claims);
        setConnected(true);
      } else {
        // Clear corrupt token
        signOut();
      }
    }
  }, [token]);

  // Check URL callback and connection on Mount
  useEffect(() => {
    // 1. Inspect URL parameters for OAuth2 redirect token
    const urlParams = new URLSearchParams(window.location.search);
    const tokenParam = urlParams.get('token');
    
    if (tokenParam) {
      localStorage.setItem('token', tokenParam);
      setToken(tokenParam);
      // Clean query string from browser address bar
      window.history.replaceState({}, document.title, window.location.pathname);
    } else if (token) {
      checkConnection();
    }
  }, []);

  // Sync default units when Measurement Type changes
  useEffect(() => {
    const defaults = DEFAULT_UNITS[measurementType];
    setUnit1(defaults.first);
    setUnit2(defaults.second);
    setTargetUnit(defaults.target);

    // If Temperature is selected, restrict active tab to supported operations (Convert & Compare)
    if (measurementType === 'TEMPERATURE' && !['convert', 'compare'].includes(activeTab)) {
      setActiveTab('convert');
    }
  }, [measurementType]);

  // Fetch History and Metrics when relevant filters change or on mount
  useEffect(() => {
    if (token && connected) {
      fetchHistory();
      fetchMetrics();
    }
  }, [token, connected, filterOp, filterErrored]);

  const checkConnection = async () => {
    if (!token) return;
    try {
      const res = await fetch('/api/v1/quantities/history?operation=ALL', {
        method: 'GET',
        headers: { 'Authorization': `Bearer ${token}` }
      });
      if (res.ok) {
        setConnected(true);
        fetchHistory();
        fetchMetrics();
      } else {
        setConnected(false);
        if (res.status === 401) {
          signOut();
        }
      }
    } catch (err) {
      setConnected(false);
    }
  };

  const fetchHistory = async () => {
    if (!token) return;
    setHistoryLoading(true);
    try {
      let url = '/api/v1/quantities/history';
      const params = [];
      if (filterErrored) {
        params.push('errored=true');
      } else if (filterOp !== 'ALL') {
        params.push(`operation=${filterOp}`);
      }
      
      if (params.length > 0) {
        url += `?${params.join('&')}`;
      }

      const res = await fetch(url, { headers: { 'Authorization': `Bearer ${token}` } });
      if (res.ok) {
        const data = await res.json();
        // Sort history by ID descending (newest first)
        setHistory(data.sort((a, b) => b.id - a.id));
      }
    } catch (err) {
      console.error("Error fetching history: ", err);
    } finally {
      setHistoryLoading(false);
    }
  };

  const fetchMetrics = async () => {
    if (!token) return;
    try {
      const ops = ['COMPARE', 'CONVERT', 'ADD', 'SUBTRACT', 'DIVIDE'];
      const counts = {};
      for (const op of ops) {
        const res = await fetch(`/api/v1/quantities/metrics?operation=${op}`, {
          headers: { 'Authorization': `Bearer ${token}` }
        });
        if (res.ok) {
          const count = await res.json();
          counts[op.toLowerCase()] = count;
        }
      }
      setMetrics(counts);
    } catch (err) {
      console.error("Error fetching metrics: ", err);
    }
  };

  const handleMockLogin = async () => {
    setLoading(true);
    try {
      const res = await fetch('/api/auth/mock-login?email=developer@quantitymeasurement.com&name=Dev User');
      if (res.ok) {
        const data = await res.json();
        localStorage.setItem('token', data.token);
        setToken(data.token);
      } else {
        alert("Failed to get mock token from backend. Ensure Spring Boot is running on port 8080!");
      }
    } catch (err) {
      alert("Failed to reach Spring Boot server. Confirm it is running!");
    } finally {
      setLoading(false);
    }
  };

  const signOut = () => {
    localStorage.removeItem('token');
    setToken('');
    setUser(null);
    setConnected(null);
    setHistory([]);
  };

  const handleExecute = async (e) => {
    e.preventDefault();
    setLoading(true);
    setResult(null);

    const payload = {
      firstQuantity: {
        value: parseFloat(val1),
        unitName: unit1,
        measurementType: measurementType
      },
      secondQuantity: {
        value: activeTab === 'convert' ? 0.0 : parseFloat(val2),
        unitName: unit2,
        measurementType: measurementType
      }
    };

    if (['add', 'subtract'].includes(activeTab)) {
      payload.targetQuantity = {
        value: 0.0,
        unitName: targetUnit,
        measurementType: measurementType
      };
    }

    try {
      const res = await fetch(`/api/v1/quantities/${activeTab}`, {
        method: 'POST',
        headers: fetchHeaders,
        body: JSON.stringify(payload)
      });

      if (res.ok) {
        const data = await res.json();
        setResult({ success: true, data });
        fetchHistory();
        fetchMetrics();
      } else {
        const errData = await res.json().catch(() => ({}));
        setResult({ 
          success: false, 
          message: errData.errorMessage || errData.message || 'Operation failed. Check measurement compatibility.' 
        });
        fetchHistory();
      }
    } catch (err) {
      setResult({ success: false, message: 'Could not reach server. Verify your Spring Boot application is running on port 8080.' });
    } finally {
      setLoading(false);
    }
  };

  const getResultString = () => {
    if (!result || !result.success) return '';
    const data = result.data;
    
    if (activeTab === 'compare') {
      const eq = data.scalarResult === 1.0;
      return eq ? 'EQUIVALENT' : 'NOT EQUIVALENT';
    }

    if (activeTab === 'convert') {
      return `${data.value.toFixed(4)} ${data.unitName}`;
    }

    if (['add', 'subtract'].includes(activeTab)) {
      return `${data.value.toFixed(4)} ${data.unitName}`;
    }

    if (activeTab === 'divide') {
      return `${data.scalarResult.toFixed(4)} (Ratio)`;
    }

    return '';
  };

  // Dynamic label for arithmetic tabs based on temperature support
  const isTemp = measurementType === 'TEMPERATURE';

  // Render Premium Login View if not Authenticated
  if (!token) {
    return (
      <div className="login-container">
        <div className="login-card glass-panel">
          <div className="login-logo">
            <Layers size={32} />
          </div>
          <h2>Quantity Measurement App</h2>
          <div className="login-subtitle">
            Secure N-Tier Quantity Measurement Engine.
            <br />
            Please sign in to proceed to the workspace.
          </div>
          
          <div className="login-buttons-group">
            <button className="btn-oauth google" onClick={() => window.location.href = 'http://localhost:8080/api/auth/login'}>
              <svg width="18" height="18" viewBox="0 0 18 18">
                <path fill="#4285F4" d="M17.6 9.2c0-.6-.05-1.2-.15-1.75H9v3.3h4.8c-.2 1.1-.8 2-1.8 2.6v2.2h2.9c1.7-1.5 2.7-3.8 2.7-6.35z"/>
                <path fill="#34A853" d="M9 18c2.4 0 4.5-.8 6-2.2l-2.9-2.2c-.8.5-1.8.8-3.1.8-2.4 0-4.4-1.6-5.1-3.8H.9v2.3C2.4 15.9 5.5 18 9 18z"/>
                <path fill="#FBBC05" d="M3.9 10.6c-.2-.5-.3-1.1-.3-1.6s.1-1.1.3-1.6V5.1H.9C.3 6.3 0 7.6 0 9s.3 2.7.9 3.9l3-2.3z"/>
                <path fill="#EA4335" d="M9 3.6c1.3 0 2.5.45 3.4 1.35l2.6-2.6C13.4.9 11.4 0 9 0 5.5 0 2.4 2.1.9 5.1l3 2.3c.7-2.2 2.7-3.8 5.1-3.8z"/>
              </svg>
              Sign in with Google OAuth
            </button>

            <div className="login-divider">or</div>

            <button className="btn-oauth mock" onClick={handleMockLogin} disabled={loading}>
              <Activity size={18} />
              {loading ? "Requesting token..." : "Bypass / Developer Sign In"}
            </button>
          </div>

          <div className="login-footer-info">
            Uses Google OpenID Connect & stateless HMAC-SHA256 signed JSON Web Tokens for secure REST API communication.
          </div>
        </div>
      </div>
    );
  }

  return (
    <div>
      {/* Navbar Header */}
      <header className="app-header glass-panel">
        <div className="brand">
          <div className="brand-icon">
            <Layers size={22} />
          </div>
          <div>
            <h1 className="brand-title">Quantity Measurement App</h1>
            <span style={{ fontSize: '0.75rem', color: 'var(--text-secondary)', fontWeight: 600 }}>
              Precision Quantity measurement N-Tier Engine
            </span>
          </div>
        </div>

        <div style={{ display: 'flex', alignItems: 'center', gap: '1.5rem' }}>
          <div className="server-status">
            {connected === null && (
              <>
                <RefreshCw size={14} className="animate-spin" />
                <span>Verifying Connection...</span>
              </>
            )}
            {connected === true && (
              <>
                <div className="status-dot connected"></div>
                <span style={{ color: '#34d399' }}>Spring Boot Server Active</span>
              </>
            )}
            {connected === false && (
              <>
                <div className="status-dot offline"></div>
                <span style={{ color: '#f87171', cursor: 'pointer', display: 'flex', alignItems: 'center', gap: '4px' }} onClick={checkConnection}>
                  Server Offline <RefreshCw size={12} />
                </span>
              </>
            )}
          </div>

          <div className="user-menu">
            <div className="user-avatar-group">
              <img 
                src={user?.picture || 'https://lh3.googleusercontent.com/a/default-profile'} 
                alt="Avatar" 
                className="user-avatar" 
              />
              <span className="user-name">{user?.name || user?.sub || 'User'}</span>
            </div>
            <button className="btn-signout" onClick={signOut}>
              Sign Out
            </button>
          </div>
        </div>
      </header>

      <main className="dashboard">
        {/* Metric Cards Row */}
        <section className="metrics-row">
          <div className="metric-card glass-panel interactive" onClick={() => { setFilterOp('CONVERT'); setFilterErrored(false); }}>
            <ArrowLeftRight size={18} className="metric-icon" />
            <div className="metric-value">{metrics.convert}</div>
            <div className="metric-label">Conversions</div>
          </div>
          
          <div className="metric-card glass-panel interactive" onClick={() => { setFilterOp('COMPARE'); setFilterErrored(false); }}>
            <Scale size={18} className="metric-icon" />
            <div className="metric-value">{metrics.compare}</div>
            <div className="metric-label">Comparisons</div>
          </div>

          <div className="metric-card glass-panel interactive" onClick={() => { setFilterOp('ADD'); setFilterErrored(false); }}>
            <Plus size={18} className="metric-icon" />
            <div className="metric-value">{metrics.add}</div>
            <div className="metric-label">Additions</div>
          </div>

          <div className="metric-card glass-panel interactive" onClick={() => { setFilterOp('SUBTRACT'); setFilterErrored(false); }}>
            <Minus size={18} className="metric-icon" />
            <div className="metric-value">{metrics.subtract}</div>
            <div className="metric-label">Subtractions</div>
          </div>

          <div className="metric-card glass-panel interactive" onClick={() => { setFilterOp('DIVIDE'); setFilterErrored(false); }}>
            <Divide size={18} className="metric-icon" />
            <div className="metric-value">{metrics.divide}</div>
            <div className="metric-label">Divisions</div>
          </div>
        </section>

        {/* Dashboard Grid */}
        <div className="dashboard-grid">
          {/* Operational Console Panel */}
          <section className="glass-panel console-card">
            <h2 className="console-title">
              <Activity size={20} /> Operational Console
            </h2>

            {/* Measurement Category Buttons */}
            <div className="type-selector">
              <button 
                className={`type-btn ${measurementType === 'LENGTH' ? 'active' : ''}`}
                onClick={() => setMeasurementType('LENGTH')}
              >
                <Layers size={18} />
                <span>Length</span>
              </button>
              <button 
                className={`type-btn ${measurementType === 'WEIGHT' ? 'active' : ''}`}
                onClick={() => setMeasurementType('WEIGHT')}
              >
                <Scale size={18} />
                <span>Weight</span>
              </button>
              <button 
                className={`type-btn ${measurementType === 'VOLUME' ? 'active' : ''}`}
                onClick={() => setMeasurementType('VOLUME')}
              >
                <Layers size={18} style={{ transform: 'rotate(90deg)' }} />
                <span>Volume</span>
              </button>
              <button 
                className={`type-btn ${measurementType === 'TEMPERATURE' ? 'active' : ''}`}
                onClick={() => setMeasurementType('TEMPERATURE')}
              >
                <Thermometer size={18} />
                <span>Temperature</span>
              </button>
            </div>

            {/* Operation Type Tabs */}
            <div className="operation-tabs">
              <button 
                className={`tab-btn ${activeTab === 'convert' ? 'active' : ''}`}
                onClick={() => setActiveTab('convert')}
              >
                <ArrowLeftRight size={14} />
                <span>Convert</span>
              </button>

              <button 
                className={`tab-btn ${activeTab === 'compare' ? 'active' : ''}`}
                onClick={() => setActiveTab('compare')}
              >
                <Scale size={14} />
                <span>Compare</span>
              </button>

              <button 
                className={`tab-btn ${activeTab === 'add' ? 'active' : ''}`}
                disabled={isTemp}
                title={isTemp ? "Temperature does not support arithmetic addition" : ""}
                onClick={() => setActiveTab('add')}
              >
                <Plus size={14} />
                <span>Add</span>
              </button>

              <button 
                className={`tab-btn ${activeTab === 'subtract' ? 'active' : ''}`}
                disabled={isTemp}
                title={isTemp ? "Temperature does not support arithmetic subtraction" : ""}
                onClick={() => setActiveTab('subtract')}
              >
                <Minus size={14} />
                <span>Subtract</span>
              </button>

              <button 
                className={`tab-btn ${activeTab === 'divide' ? 'active' : ''}`}
                disabled={isTemp}
                title={isTemp ? "Temperature does not support division" : ""}
                onClick={() => setActiveTab('divide')}
              >
                <Divide size={14} />
                <span>Divide</span>
              </button>
            </div>

            {isTemp && ['add', 'subtract', 'divide'].includes(activeTab) && (
              <div style={{ background: 'rgba(239, 68, 68, 0.08)', border: '1px solid rgba(239, 68, 68, 0.2)', padding: '1rem', borderRadius: '8px', color: '#f87171', fontSize: '0.85rem', marginBottom: '1.5rem', display: 'flex', alignItems: 'center', gap: '8px' }}>
                <AlertCircle size={16} />
                <span>Arithmetic operations are not mathematically valid or supported for Temperature. Please select Convert or Compare.</span>
              </div>
            )}

            {/* Calculations Form */}
            <form onSubmit={handleExecute}>
              <div className="form-grid">
                {/* First Quantity Block */}
                <div className="form-group">
                  <label>Quantity 1 (Value)</label>
                  <input 
                    type="number" 
                    step="any"
                    required
                    className="input-field" 
                    value={val1}
                    onChange={(e) => setVal1(e.target.value)}
                  />
                </div>

                <div className="form-group">
                  <label>Unit 1</label>
                  <select 
                    className="select-field"
                    value={unit1}
                    onChange={(e) => setUnit1(e.target.value)}
                  >
                    {UNITS[measurementType].map(unit => (
                      <option key={unit.value} value={unit.value}>{unit.label}</option>
                    ))}
                  </select>
                </div>

                {/* Second Quantity Block (Hidden during convert operation) */}
                {activeTab !== 'convert' && (
                  <>
                    <div className="form-group">
                      <label>Quantity 2 (Value)</label>
                      <input 
                        type="number" 
                        step="any"
                        required
                        className="input-field" 
                        value={val2}
                        onChange={(e) => setVal2(e.target.value)}
                      />
                    </div>

                    <div className="form-group">
                      <label>Unit 2</label>
                      <select 
                        className="select-field"
                        value={unit2}
                        onChange={(e) => setUnit2(e.target.value)}
                      >
                        {UNITS[measurementType].map(unit => (
                          <option key={unit.value} value={unit.value}>{unit.label}</option>
                        ))}
                      </select>
                    </div>
                  </>
                )}

                {/* Target Unit (For Add & Subtract) */}
                {['add', 'subtract'].includes(activeTab) && (
                  <div className="form-group full-width">
                    <label>Target Output Unit</label>
                    <select 
                      className="select-field"
                      value={targetUnit}
                      onChange={(e) => setTargetUnit(e.target.value)}
                    >
                      {UNITS[measurementType].map(unit => (
                        <option key={unit.value} value={unit.value}>{unit.label}</option>
                      ))}
                    </select>
                  </div>
                )}

                {/* Convert Target Unit selector (Shown in convert instead of second quantity) */}
                {activeTab === 'convert' && (
                  <div className="form-group full-width">
                    <label>Target Unit to Convert to</label>
                    <select 
                      className="select-field"
                      value={unit2}
                      onChange={(e) => setUnit2(e.target.value)}
                    >
                      {UNITS[measurementType].map(unit => (
                        <option key={unit.value} value={unit.value}>{unit.label}</option>
                      ))}
                    </select>
                  </div>
                )}
              </div>

              {/* Submit trigger button */}
              <button 
                type="submit" 
                className="submit-btn"
                disabled={loading || (isTemp && ['add', 'subtract', 'divide'].includes(activeTab))}
              >
                {loading ? (
                  <>
                    <RefreshCw size={18} className="animate-spin" />
                    <span>Processing Calculation...</span>
                  </>
                ) : (
                  <>
                    {activeTab === 'convert' && <ArrowLeftRight size={18} />}
                    {activeTab === 'compare' && <Scale size={18} />}
                    {activeTab === 'add' && <Plus size={18} />}
                    {activeTab === 'subtract' && <Minus size={18} />}
                    {activeTab === 'divide' && <Divide size={18} />}
                    <span>
                      Execute {activeTab.charAt(0).toUpperCase() + activeTab.slice(1)}
                    </span>
                  </>
                )}
              </button>
            </form>

            {/* Beautiful Output Display */}
            {result && (
              <div className={`result-card ${result.success ? 'success' : 'error'}`}>
                {result.success ? (
                  <>
                    <div className="result-label">Resulting Output</div>
                    <div className="result-value">
                      {getResultString()}
                    </div>
                    {activeTab === 'compare' && (
                      <div className={`result-badge ${result.data.scalarResult === 1.0 ? 'eq' : 'neq'}`}>
                        {result.data.scalarResult === 1.0 ? 'Equivalent' : 'Not Equal'}
                      </div>
                    )}
                  </>
                ) : (
                  <>
                    <div className="result-label" style={{ color: '#f87171' }}>Calculation Error</div>
                    <div className="result-value" style={{ fontSize: '1rem', color: '#f87171' }}>
                      <AlertCircle size={28} style={{ marginBottom: '8px', color: '#ef4444' }} />
                      <div>{result.message}</div>
                    </div>
                  </>
                )}
              </div>
            )}
          </section>

          {/* Interactive Database History Log Panel */}
          <section className="glass-panel history-card">
            <div className="history-header">
              <h2 className="history-title">
                <Database size={18} /> Operation Log
              </h2>
              <button 
                onClick={fetchHistory}
                disabled={historyLoading}
                style={{ background: 'transparent', border: 'none', color: 'var(--text-secondary)', cursor: 'pointer' }}
                title="Refresh log"
              >
                <RefreshCw size={16} className={historyLoading ? 'animate-spin' : ''} />
              </button>
            </div>

            {/* Filter tools */}
            <div className="history-filters">
              <select 
                className="filter-select"
                value={filterOp}
                onChange={(e) => { setFilterOp(e.target.value); setFilterErrored(false); }}
              >
                <option value="ALL">All Operations</option>
                <option value="CONVERT">Conversions</option>
                <option value="COMPARE">Comparisons</option>
                <option value="ADD">Additions</option>
                <option value="SUBTRACT">Subtractions</option>
                <option value="DIVIDE">Divisions</option>
              </select>

              <label className="errored-toggle">
                <input 
                  type="checkbox" 
                  checked={filterErrored}
                  onChange={(e) => setFilterErrored(e.target.checked)}
                />
                <span>Show Errored Entries Only</span>
              </label>
            </div>

            {/* List entries */}
            <div className="history-list">
              {historyLoading && history.length === 0 ? (
                <div className="empty-history">
                  <RefreshCw size={24} className="animate-spin" style={{ margin: '0 auto 10px auto', display: 'block', color: 'var(--border-color-glow)' }} />
                  <span>Loading history from MySQL database...</span>
                </div>
              ) : history.length === 0 ? (
                <div className="empty-history">
                  <History size={32} style={{ margin: '0 auto 10px auto', display: 'block', opacity: 0.3 }} />
                  <span>No recorded operations found matching filters.</span>
                </div>
              ) : (
                history.map((item) => (
                  <div 
                    key={item.id} 
                    className={`history-item ${item.hasError ? 'errored-item' : ''}`}
                  >
                    <div className="history-meta">
                      <span className={`op-badge ${item.hasError ? 'error' : item.operationType.toLowerCase()}`}>
                        {item.operationType}
                      </span>
                      <span className="history-type-tag">{item.measurementType}</span>
                      <span>
                        {new Date(item.createdAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' })}
                      </span>
                    </div>

                    {item.hasError ? (
                      <>
                        <div className="history-expression" style={{ color: 'var(--text-secondary)' }}>
                          {item.input1Value} ↔ {item.input2Value}
                        </div>
                        <div className="history-result" style={{ fontSize: '0.8rem', display: 'flex', alignItems: 'center', gap: '4px' }}>
                          <AlertCircle size={12} /> {item.errorMessage}
                        </div>
                      </>
                    ) : (
                      <>
                        <div className="history-expression">
                          {item.operationType === 'CONVERT' && (
                            <span>Convert {item.input1Value} to {item.targetUnit}</span>
                          )}
                          {item.operationType === 'COMPARE' && (
                            <span>Compare {item.input1Value} and {item.input2Value}</span>
                          )}
                          {item.operationType === 'ADD' && (
                            <span>Add {item.input1Value} + {item.input2Value} in {item.targetUnit}</span>
                          )}
                          {item.operationType === 'SUBTRACT' && (
                            <span>Subtract {item.input1Value} - {item.input2Value} in {item.targetUnit}</span>
                          )}
                          {item.operationType === 'DIVIDE' && (
                            <span>Divide {item.input1Value} by {item.input2Value}</span>
                          )}
                        </div>
                        <div className="history-result">
                          = {item.resultValue.includes('Quantity') || item.resultValue.includes('Scalar') || item.resultValue.includes('Error') 
                              ? item.resultValue.replace('Quantity(', '').replace('Scalar(', '').replace(')', '').replace(', ', ' ')
                              : item.resultValue === 'true' ? 'EQUIVALENT' : item.resultValue === 'false' ? 'NOT EQUIVALENT' : item.resultValue
                            }
                        </div>
                      </>
                    )}
                  </div>
                ))
              )}
            </div>
          </section>
        </div>
      </main>
    </div>
  );
}
