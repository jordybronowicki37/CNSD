import {useContext} from "react";
import {WebSocketContext} from "../data/webSocket/WebSocketContext.ts";

export function ConnectionStatus() {
  const webSocket = useContext(WebSocketContext);
  let status: string;

  switch (webSocket?.readyState) {
    case -1:
      status = "⚙️";
      break;
    case 0:
      status = "🕓";
      break;
    case 1:
      status = "✅";
      break;
    case 2:
      status = "🛑";
      break;
    case 3:
      status = "❌";
      break;
    default:
      status = "⁉️"
  }

  return (<div>Status: {status}</div>)
}