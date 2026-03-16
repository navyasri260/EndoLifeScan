import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Scan, Eye, Sun, Layers, AlertTriangle } from 'lucide-react';
import './GuideFlow.css';

const guides = [
  {
    id: 'intro',
    navTitle: 'Scan Guide',
    icon: <Scan size={40} className="text-purple-600" />,
    iconBg: 'bg-purple-100',
    title: 'How to Scan Endodontic Files',
    desc: "To ensure accurate AI analysis, you'll need to capture three specific views of your instrument.",
    boxType: 'default',
    boxTitle: 'Required Views:',
    boxContent: (
      <ul className="guide-list">
        <li>• Front View (Full length)</li>
        <li>• Tip View (Close-up)</li>
        <li>• Straight View (Alignment)</li>
      </ul>
    ),
    btnText: 'Start Guide',
  },
  {
    id: 'clarity',
    navTitle: 'Guide: Clarity',
    icon: <Eye size={40} className="text-purple-600" />,
    iconBg: 'bg-purple-100',
    title: 'Why a clear image is required',
    desc: 'The AI needs to see minute details like micro-cracks and surface wear on the metal file. A blurry or out-of-focus image will result in inaccurate analysis.',
    boxType: 'default',
    boxContent: 'Ensure the camera lens is clean before scanning.',
    btnText: 'Next Step',
  },
  {
    id: 'lighting',
    navTitle: 'Guide: Lighting',
    icon: <Sun size={40} className="text-amber-500" />,
    iconBg: 'bg-amber-100',
    title: 'Lighting Tips',
    desc: 'Use bright, even lighting. Natural daylight is often best. Avoid using the camera flash if it creates glare on the metal surface.',
    boxType: 'default',
    boxContent: 'Ensure there are no strong shadows cast over the file.',
    btnText: 'Next Step',
  },
  {
    id: 'background',
    navTitle: 'Guide: Background',
    icon: <Layers size={40} className="text-slate-500" />,
    iconBg: 'bg-slate-100',
    title: 'Background Tips',
    desc: 'Place the file on a plain, solid-colored surface. A white or dark background usually provides the best contrast for the metal file.',
    boxType: 'default',
    boxContent: 'Avoid patterned or textured surfaces.',
    btnText: 'Next Step',
  },
  {
    id: 'mistakes',
    navTitle: 'Guide: Mistakes',
    icon: <AlertTriangle size={40} className="text-red-500" />,
    iconBg: 'bg-red-100',
    title: 'Common Mistakes',
    desc: '',
    boxType: 'none',
    boxContent: (
      <ul className="mistakes-list">
        <li><span className="red-dot"></span> Blurry or shaky images</li>
        <li><span className="red-dot"></span> Shadows covering the file</li>
        <li><span className="red-dot"></span> Only part of the file visible</li>
      </ul>
    ),
    btnText: 'Next Step',
  }
];

const GuideFlow = () => {
  const [step, setStep] = useState(0);
  const navigate = useNavigate();

  const currentGuide = guides[step];

  const handleNext = () => {
    if (step < guides.length - 1) {
      setStep(step + 1);
    } else {
      navigate('/scan');
    }
  };

  const handleBack = () => {
    if (step > 0) {
      setStep(step - 1);
    } else {
      navigate('/');
    }
  };

  return (
    <div className="guide-page-container w-full min-h-screen bg-white flex flex-col items-center">
      {/* Navbar overlay for guide */}
      <div className="guide-nav w-full max-w-lg flex items-center p-4 pt-6">
        <button onClick={handleBack} className="guide-back-btn p-2 hover:bg-slate-100 rounded-full transition-colors mr-3">
          <ArrowLeft size={24} className="text-slate-800" />
        </button>
        <span className="guide-nav-title text-xl font-medium text-slate-800">{currentGuide.navTitle}</span>
      </div>

      <div className="guide-content flex-grow flex flex-col items-center w-full max-w-md px-6 pt-10 pb-24 text-center">
        
        <div className={`guide-icon-wrapper ${currentGuide.iconBg} w-24 h-24 rounded-full flex items-center justify-center mb-8 shadow-sm`}>
          {currentGuide.icon}
        </div>

        <h2 className="guide-title text-2xl font-bold text-slate-800 mb-6">{currentGuide.title}</h2>
        
        {currentGuide.desc && (
          <p className="guide-desc text-slate-500 text-lg leading-relaxed mb-8">
            {currentGuide.desc}
          </p>
        )}

        {currentGuide.boxType === 'default' && currentGuide.boxContent && (
          <div className="guide-box bg-slate-50 border border-blue-200 rounded-xl p-5 text-left w-full shadow-sm text-slate-600 text-md leading-relaxed">
            {currentGuide.boxTitle && <h4 className="font-semibold text-slate-800 mb-2">{currentGuide.boxTitle}</h4>}
            {currentGuide.boxContent}
          </div>
        )}

        {currentGuide.boxType === 'none' && currentGuide.boxContent}

      </div>

      <div className="guide-footer fixed bottom-0 left-0 w-full p-6 flex justify-center bg-white border-t border-slate-100">
        <button 
          onClick={handleNext}
          className="guide-primary-btn w-full max-w-md bg-purple-700 hover:bg-purple-800 text-white font-medium text-lg py-4 rounded-full transition-colors shadow-md shadow-purple-200"
        >
          {currentGuide.btnText}
        </button>
      </div>
    </div>
  );
};

export default GuideFlow;
