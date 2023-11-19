import './App.css'
import {OverviewPage} from "./pages/OverviewPage.tsx";
import {useConfiguredWebSocket} from "./data/webSocket/UseConfiguredWebSocket.ts";
import {WebSocketContext} from "./data/webSocket/WebSocketContext.ts";

export function App() {
  const webSocket = useConfiguredWebSocket();

  return (
    <WebSocketContext.Provider value={webSocket}>
      <OverviewPage />
    </WebSocketContext.Provider>
  )
}
