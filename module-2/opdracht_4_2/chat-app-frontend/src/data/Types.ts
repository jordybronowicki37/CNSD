export type WSSendEvent<D> = {
  action: string,
  data: WSReceiveEvent<D>,
}

export type WSReceiveEvent<D> = {
  type: EventTypes,
  content: D
}

export enum EventTypes {
  NewMessageEvent = "NewMessageEvent"
}

export type Message = {
  date: number,
  message: string,
  sender: string,
}
