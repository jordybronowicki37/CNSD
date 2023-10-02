import {createAction, createReducer} from "@reduxjs/toolkit";
import {Holder} from "../Types.ts";

export const holderAddAction = createAction<Holder>("holder/add");
export const holdersSetAction = createAction<Holder[]>("holders/set");

export const holderReducer = createReducer<Holder[]>([], builder => {
  builder
    .addCase(holderAddAction, (state, action) => [...state, action.payload])
    .addCase(holdersSetAction, (_, action) => [...action.payload])
})
