import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import {Store} from "./data/DataStore.ts";
import {Provider} from "react-redux";

ReactDOM.createRoot(document.getElementById('root')!).render(
  <Provider store={Store}>
    <App />
  </Provider>
)
