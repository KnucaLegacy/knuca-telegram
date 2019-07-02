import React from 'react';
import ReactDOM from 'react-dom';
import ReactGA from 'react-ga';
import 'bootstrap/dist/css/bootstrap.css';
import './css/open-iconic-bootstrap.min.css';
import App from './components/App';

ReactGA.initialize('UA-112398090-2');
ReactGA.pageview(window.location.pathname);

ReactDOM.render(<App />, document.getElementById('root'));
