import './App.css'
import {HomePage} from "./pages/HomePage.tsx";
import {useConfiguredWebSocket} from "./data/webSocket/UseConfiguredWebSocket.ts";
import {WebSocketContext} from "./data/webSocket/WebSocketContext.ts";

function App() {
  const webSocket = useConfiguredWebSocket();

  return (
    <WebSocketContext.Provider value={webSocket}>
      <div id="app-layout">
        <HomePage />
      </div>
    </WebSocketContext.Provider>
  )
}

export default App
