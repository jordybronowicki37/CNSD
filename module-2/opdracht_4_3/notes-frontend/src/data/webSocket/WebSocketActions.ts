import {OutgoingEventTypes, WSSendEvent} from "../Types.ts";
import {Store} from "../DataStore.ts";

export function CreateNoteEvent(text: string): WSSendEvent<any> {
  const user = Store.getState().currentUser;
  return {
    action: OutgoingEventTypes.CreateNoteEvent,
    data: {
      action: OutgoingEventTypes.CreateNoteEvent,
      user_id: user,
      text,
    }
  }
}