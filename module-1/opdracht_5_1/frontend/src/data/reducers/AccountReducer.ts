import {createAction, createReducer} from "@reduxjs/toolkit";
import {Account} from "../Types.ts";

export const accountAddAction = createAction<Account>("account/add");
export const accountsSetAction = createAction<Account[]>("accounts/set");

export const accountReducer = createReducer<Account[]>([], builder => {
  builder
    .addCase(accountAddAction, (state, action) => [...state, action.payload])
    .addCase(accountsSetAction, (_, action) => [...action.payload])
})
