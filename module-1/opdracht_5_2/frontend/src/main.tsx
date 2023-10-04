import ReactDOM from 'react-dom/client'
import {App} from './App.tsx'
import './index.css'
import "../axios-config.js"
import {HashRouter} from "react-router-dom";
import {Provider} from "react-redux";
import {Store} from "./data/DataStore.ts";

const rootElement = document.getElementById('root');

ReactDOM.createRoot(rootElement!).render(
  <HashRouter>
    <Provider store={Store}>
      <App />
    </Provider>
  </HashRouter>
);
