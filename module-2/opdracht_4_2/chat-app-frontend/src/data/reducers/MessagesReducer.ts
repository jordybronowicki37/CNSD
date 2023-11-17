import {createAction, createReducer} from "@reduxjs/toolkit";
import {Message} from "../Types.ts";

export const messagesAddAction = createAction<Message>("messages/add");
export const messagesSetAction = createAction<Message[]>("messages/set");

export const messagesReducer = createReducer<Message[]>([], builder => {
  builder
    .addCase(messagesAddAction, (state, action) => [...state, action.payload])
    .addCase(messagesSetAction, (_, action) => [...action.payload])
})
