import React, { useState, useRef } from 'react';
import api from '../services/api';
import useAuth from '../hooks/useAuth';
import { Upload, Image as ImageIcon, X, AlertCircle, Activity, ShieldCheck, ShieldAlert, AlertTriangle } from 'lucide-react';
import './Dashboard.css';

const Dashboard = () => {
  const { user } = useAuth();
  const [images, setImages] = useState({ image1: null, image2: null, image3: null });
  const [previews, setPreviews] = useState({ image1: '', image2: '', image3: '' });
  const [isUploading, setIsUploading] = useState(false);
  const [error, setError] = useState('');
  
  // Results State
  const [results, setResults] = useState(null);
  const [resultView, setResultView] = useState('full'); // 'tip' or 'full'

  const handleImageChange = (e, key) => {
    const file = e.target.files[0];
    if (file) {
      setImages(prev => ({ ...prev, [key]: file }));
      
      const reader = new FileReader();
      reader.onloadend = () => {
        setPreviews(prev => ({ ...prev, [key]: reader.result }));
      };
      reader.readAsDataURL(file);
    }
  };

  const removeImage = (key) => {
    setImages(prev => ({ ...prev, [key]: null }));
    setPreviews(prev => ({ ...prev, [key]: '' }));
    setResults(null); // Clear previous results if modifying images
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!images.image1 || !images.image2 || !images.image3) {
      setError('Please provide all three images (Front, Middle, and Tip segments).');
      return;
    }

    setIsUploading(true);
    setError('');
    setResults(null);
    setResultView('tip'); // Show the tip inspection screen first

    const formData = new FormData();
    formData.append('image1', images.image1);
    formData.append('image2', images.image2);
    formData.append('image3', images.image3);
    formData.append('user_id', user.id);

    try {
      const response = await api.post('/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      
      if (response.data.status === 'success') {
        setResults(response.data);
      } else {
        setError(response.data.message || 'Analysis failed.');
      }
    } catch (err) {
      console.error(err);
      setError(err.response?.data?.message || 'An error occurred during file upload.');
    } finally {
      setIsUploading(false);
    }
  };

  const resetForm = () => {
    setImages({ image1: null, image2: null, image3: null });
    setPreviews({ image1: '', image2: '', image3: '' });
    setResults(null);
    setResultView('full');
    setError('');
  };

  const getStatusColor = (prediction) => {
    switch (prediction) {
      case 'safe': return 'text-emerald-500';
      case 'borderline': return 'text-amber-500';
      case 'not_safe': return 'text-red-500';
      default: return 'text-slate-400';
    }
  };

  const getStatusIcon = (prediction) => {
    switch (prediction) {
      case 'safe': return <ShieldCheck size={32} className="text-emerald-500" />;
      case 'borderline': return <AlertTriangle size={32} className="text-amber-500" />;
      case 'not_safe': return <ShieldAlert size={32} className="text-red-500" />;
      default: return <Activity size={32} />;
    }
  };

  // Helper component for image upload box
  const ImageUploadBox = ({ title, imgKey }) => {
    const inputRef = useRef(null);
    
    return (
      <div className="upload-box">
        <div className="upload-box-header">
          <span className="upload-box-title">{title}</span>
        </div>
        
        {previews[imgKey] ? (
          <div className="image-preview-container">
            <img src={previews[imgKey]} alt={`${title} preview`} className="image-preview" />
            <button 
              type="button"
              className="remove-image-btn"
              onClick={() => removeImage(imgKey)}
            >
              <X size={16} />
            </button>
          </div>
        ) : (
          <div 
            className="upload-placeholder"
            onClick={() => inputRef.current?.click()}
          >
            <ImageIcon size={32} className="placeholder-icon" />
            <span className="placeholder-text">Click to upload</span>
            <input 
              ref={inputRef}
              type="file" 
              accept="image/*" 
              className="hidden-input"
              onChange={(e) => handleImageChange(e, imgKey)}
            />
          </div>
        )}
      </div>
    );
  };

  return (
    <div className="page-container dashboard-container animate-fade-in">
      <header className="dashboard-header mb-8">
        <h1 className="text-3xl font-bold mb-2">Workspace</h1>
        <p className="text-slate-400 text-lg">Upload endodontic file segments to analyze structural integrity</p>
      </header>

      {error && (
        <div className="alert-error mb-6">
          <AlertCircle size={20} />
          <span>{error}</span>
        </div>
      )}

      <div className="dashboard-grid">
        {/* Left Column - Input */}
        <div className="input-section glass-panel">
          <h2 className="section-title">File Segments</h2>
          
          <form onSubmit={handleSubmit} className="upload-form">
            <div className="upload-boxes-grid">
              <ImageUploadBox title="Front Segment" imgKey="image1" />
              <ImageUploadBox title="Middle Segment" imgKey="image2" />
              <ImageUploadBox title="Tip Segment" imgKey="image3" />
            </div>

            <button 
              type="submit" 
              className="btn-primary w-full mt-8"
              disabled={isUploading || !images.image1 || !images.image2 || !images.image3}
            >
              {isUploading ? (
                <>
                  <Activity className="spinner" size={20} />
                  Analyzing Features...
                </>
              ) : (
                <>
                  <Upload size={20} />
                  Start AI Analysis
                </>
              )}
            </button>
            <p className="form-hint">Analysis requires processing through both CNN and Roboflow detection modules.</p>
          </form>
        </div>

        {/* Right Column - Results */}
        <div className={`results-section glass-panel ${!results ? 'empty-state' : ''}`}>
          {!results && !isUploading && (
            <div className="empty-results">
              <Activity size={48} className="empty-icon" />
              <h3>Awaiting Analysis</h3>
              <p>Upload images and click analyze to view AI fatigue prediction results.</p>
            </div>
          )}

          {isUploading && (
            <div className="loading-results">
              <div className="pulse-ring"></div>
              <p>Generating heatmaps and computing fatigue score...</p>
            </div>
          )}

          {results && !isUploading && resultView === 'tip' && (
            <div className="analysis-results animate-fade-in flex flex-col items-center justify-center p-4">
              <h3 className="text-2xl font-bold mb-3 text-white text-center">Magnified Tip Inspection</h3>
              <p className="text-slate-400 mb-8 text-sm max-w-sm mx-auto text-center">AI models have isolated the tip segment for initial structural assessment to check for immediate micro-fractures.</p>
              
              {results.heatmaps && results.heatmaps.length > 2 && (
                <div className="mb-8 relative group inline-block">
                  <div className="absolute -inset-1 bg-gradient-to-r from-blue-500 to-cyan-500 rounded-xl blur opacity-30 group-hover:opacity-60 transition duration-1000"></div>
                  <div className="relative rounded-xl border border-slate-700 overflow-hidden shadow-2xl bg-slate-900 border-2 border-slate-600">
                    <img 
                      src={`${api.defaults.baseURL}uploads/${results.heatmaps[2]}`} 
                      alt="Magnified Tip Heatmap" 
                      className="w-full max-w-[320px] max-h-[220px] object-cover"
                      onError={(e) => { e.target.src = previews.image3 }}
                    />
                    <div className="bg-slate-800 p-3 text-center border-t border-slate-700">
                       <span className="bg-blue-500/20 text-blue-400 px-3 py-1 rounded-full text-xs font-semibold uppercase tracking-wider">AI Tip Focus</span>
                    </div>
                  </div>
                </div>
              )}

              {/* Dynamic Tip Summary */}
              <div className={`mb-6 p-4 rounded-lg w-full max-w-sm border ${
                results.prediction === 'not_safe' ? 'bg-red-500/10 border-red-500/30' : 
                results.prediction === 'borderline' ? 'bg-amber-500/10 border-amber-500/30' : 
                'bg-emerald-500/10 border-emerald-500/30'
              }`}>
                <h4 className={`font-semibold mb-1 ${getStatusColor(results.prediction)} flex items-center gap-2`}>
                  {getStatusIcon(results.prediction)}
                  Tip Condition: {
                     results.prediction === 'not_safe' ? 'DEFORMED OR CHIPPED' :
                     results.prediction === 'borderline' ? 'MODERATE FATIGUE' :
                     'VERIFIED SAFE'
                  }
                </h4>
                <p className="text-slate-300 text-sm">
                  {results.prediction === 'not_safe' 
                    ? 'Tip is deformed or chipped. Better not to use for severe damage.'
                    : results.prediction === 'borderline'
                    ? 'Tip shows moderate micro-fractures. Use with caution or consider discarding.'
                    : 'File is in a good state. Tip is verified and safe for reuse.'}
                </p>
              </div>
              
              <button 
                onClick={() => setResultView('full')}
                className="btn-primary w-full mt-2"
              >
                🔎 View Full Result
              </button>
            </div>
          )}

          {results && !isUploading && resultView === 'full' && (
            <div className="analysis-results animate-fade-in">
              <div className="result-header">
                {getStatusIcon(results.prediction)}
                <div>
                  <h3 className="result-title">Analysis Complete</h3>
                  <span className={`status-badge ${results.prediction}`}>
                    {results.prediction.replace('_', ' ').toUpperCase()}
                  </span>
                </div>
              </div>

              <div className="score-metrics-grid mt-6">
                <div className="metric-card">
                  <span className="metric-label">Fatigue Score</span>
                  <div className="metric-value">
                    <span className="text-3xl font-bold">{results.fatigue_score.toFixed(1)}</span>
                    <span className="text-sm text-slate-400">%</span>
                  </div>
                  <div className="progress-bar-bg mt-2">
                    <div 
                      className="progress-bar-fill fatigue-fill"
                      style={{ width: `${results.fatigue_score}%` }}
                    ></div>
                  </div>
                </div>

                <div className="metric-card">
                  <span className="metric-label">Structural Integrity</span>
                  <div className="metric-value">
                    <span className="text-3xl font-bold">{results.structural_integrity.toFixed(1)}</span>
                    <span className="text-sm text-slate-400">%</span>
                  </div>
                  <div className="progress-bar-bg mt-2">
                    <div 
                      className="progress-bar-fill integrity-fill"
                      style={{ width: `${results.structural_integrity}%` }}
                    ></div>
                  </div>
                </div>
              </div>

              <div className="recommendation-box mt-6">
                <h4>Recommendation</h4>
                <p>{results.recommendation}</p>
              </div>

              {/* Heatmaps Display */}
              {results.heatmaps && results.heatmaps.length > 0 && (
                 <div className="heatmaps-section mt-8">
                   <h4 className="mb-4">Generated Heatmaps</h4>
                   <div className="heatmaps-grid">
                     {results.heatmaps.map((filename, idx) => (
                        <div key={idx} className="heatmap-container">
                          <img 
                            src={`${api.defaults.baseURL}uploads/${filename}`} 
                            alt={`Heatmap ${idx + 1}`} 
                            className="heatmap-img"
                            onError={(e) => { e.target.src = previews['image'+(idx+1)] }}
                          />
                          <span className="heatmap-label text-xs mt-2 block text-center text-slate-400">
                             {['Front', 'Middle', 'Tip'][idx]}
                          </span>
                        </div>
                     ))}
                   </div>
                 </div>
              )}

              <button 
                onClick={resetForm}
                className="btn-outline w-full mt-6"
              >
                Analyze Another File
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
