import {WSReceiveEvent, WSSendEvent} from "../Types.ts";
import useWebSocket from "react-use-websocket";
import {Store} from "../DataStore.ts";
import {WebSocketHook} from "react-use-websocket/dist/lib/types";
import {IncomingEventTypes, Note} from "../Types.ts";
import {notesAddAction, notesEditAction, notesRemoveAction} from "../reducers/NotesReducer.ts";

export function useConfiguredWebSocket(): WebSocketHook<WSSendEvent<any>, MessageEvent<any> | null> {
  return useWebSocket<WSSendEvent<any>>(import.meta.env.VITE_WS_URI, {
    share: true,
    onOpen:  () => console.log("Connection opened ✅"),
    onClose: () => console.log("Connection closed 🛑"),
    onError: () => console.log("Connection error ❌"),
    shouldReconnect: () => true,
    onMessage: (e: MessageEvent<string>) => {
      const eventBody = JSON.parse(e.data) as WSReceiveEvent<Note>;

      switch (eventBody.action) {
        case IncomingEventTypes.NoteCreatedEvent:
          Store.dispatch(notesAddAction(eventBody.data));
          break;
        case IncomingEventTypes.NoteUpdatedEvent:
          Store.dispatch(notesEditAction(eventBody.data));
          break;
        case IncomingEventTypes.NoteDeletedEvent:
          Store.dispatch(notesRemoveAction(eventBody.data));
          break;
        default:
          console.error("An unrecognised event was received.");
          console.error(e);
      }
    },
  });
}
