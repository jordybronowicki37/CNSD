import './App.css'
import {HomePage} from "./pages/HomePage.tsx";
import useWebSocket from "react-use-websocket";
import {useDispatch} from "react-redux";
import {EventTypes, Message, WSReceiveEvent} from "./data/Types";
import {messagesAddAction} from "./data/reducers/MessagesReducer.ts";

function App() {
  const dispatch = useDispatch();

  useWebSocket(import.meta.env.VITE_WS_URI, {
    share: true,
    onOpen:  () => console.log("Connection opened ✅"),
    onClose: () => console.log("Connection closed 🛑"),
    onError: () => console.log("Connection error ❌"),
    onMessage: (e) => {
      const data = JSON.parse(e.data) as WSReceiveEvent<Message>;

      if (data.type === EventTypes.NewMessageEvent) {
        dispatch(messagesAddAction(data.content));
      } else {
        console.error("An unrecognised event was received.");
        console.error(e);
      }
    },
  });

  return (
    <div>
      <HomePage />
    </div>
  )
}

export default App
