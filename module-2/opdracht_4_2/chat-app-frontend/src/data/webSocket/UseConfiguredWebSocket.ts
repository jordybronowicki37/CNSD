import {EventTypes, Message, WSReceiveEvent, WSSendEvent} from "../Types.ts";
import useWebSocket from "react-use-websocket";
import {messagesAddAction} from "../reducers/MessagesReducer.ts";
import {Store} from "../DataStore.ts";
import {WebSocketHook} from "react-use-websocket/dist/lib/types";

export function useConfiguredWebSocket(): WebSocketHook<WSSendEvent<any>, MessageEvent<any> | null> {
  return useWebSocket<WSSendEvent<any>>(import.meta.env.VITE_WS_URI, {
    share: true,
    onOpen:  () => console.log("Connection opened ✅"),
    onClose: () => console.log("Connection closed 🛑"),
    onError: () => console.log("Connection error ❌"),
    shouldReconnect: () => true,
    onMessage: (e) => {
      const data = JSON.parse(e.data) as WSReceiveEvent<Message>;

      if (data.type === EventTypes.NewMessageEvent) {
        Store.dispatch(messagesAddAction(data.content));
      } else {
        console.error("An unrecognised event was received.");
        console.error(e);
      }
    },
  });
}
