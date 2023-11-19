export type WSSendEvent<D> = {
  action: string,
  data: {
    type: OutgoingEventTypes,
    content: D
  },
}

export enum OutgoingEventTypes {
  CreateNoteEvent = "post_note",
  UpdateNoteEvent = "put_note",
  DeleteNoteEvent = "delete_note",
}

export type WSReceiveEvent<D> = {
  type: IncomingEventTypes,
  content: D
}

export enum IncomingEventTypes {
  NoteCreatedEvent = "note_created",
  NoteUpdatedEvent = "note_updated",
  NoteDeletedEvent = "note_deleted",
}

export type Note = {
  PK: string,
  SK: string,
  Text: string,
  Type: string,
}