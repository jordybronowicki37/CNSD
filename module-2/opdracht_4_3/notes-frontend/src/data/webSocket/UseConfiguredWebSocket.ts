import {WSReceiveEvent, WSSendEvent} from "../Types.ts";
import useWebSocket from "react-use-websocket";
import {Store} from "../DataStore.ts";
import {WebSocketHook} from "react-use-websocket/dist/lib/types";
import {IncomingEventTypes, Note} from "../Types";
import {notesAddAction, notesEditAction, notesRemoveAction} from "../reducers/NotesReducer.ts";

export function useConfiguredWebSocket(): WebSocketHook<WSSendEvent<any>, MessageEvent<any> | null> {
  return useWebSocket<WSSendEvent<any>>(import.meta.env.VITE_WS_URI, {
    share: true,
    onOpen:  () => console.log("Connection opened ✅"),
    onClose: () => console.log("Connection closed 🛑"),
    onError: () => console.log("Connection error ❌"),
    shouldReconnect: () => true,
    onMessage: (e: MessageEvent<string>) => {
      const data = JSON.parse(e.data) as WSReceiveEvent<Note>;

      switch (data.type) {
        case IncomingEventTypes.NoteCreatedEvent:
          Store.dispatch(notesAddAction(data.content));
          break;
        case IncomingEventTypes.NoteUpdatedEvent:
          Store.dispatch(notesEditAction(data.content));
          break;
        case IncomingEventTypes.NoteDeletedEvent:
          Store.dispatch(notesRemoveAction(data.content));
          break;
        default:
          console.error("An unrecognised event was received.");
          console.error(e);
      }
    },
  });
}
