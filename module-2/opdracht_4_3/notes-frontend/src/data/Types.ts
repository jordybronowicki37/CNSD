export type WSSendEvent<D> = {
  action: OutgoingEventTypes,
  data: D
}

export enum OutgoingEventTypes {
  CreateNoteEvent = "notepost",
  UpdateNoteEvent = "noteput",
  DeleteNoteEvent = "notedelete",
}

export type WSReceiveEvent<D> = {
  action: IncomingEventTypes,
  data: D
}

export enum IncomingEventTypes {
  NoteCreatedEvent = "note/created",
  NoteUpdatedEvent = "note/updated",
  NoteDeletedEvent = "note/deleted",
}

export type Note = {
  Id: string,
  User: string
  Text: string,
  Type: string,
}