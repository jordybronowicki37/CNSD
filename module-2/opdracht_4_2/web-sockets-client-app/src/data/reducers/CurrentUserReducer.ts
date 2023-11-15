import {createAction, createReducer} from "@reduxjs/toolkit";

export const currentUserSetAction = createAction<string>("user/set");

export const currentUserReducer = createReducer<string>("", builder => {
  builder.addCase(currentUserSetAction, (_, action) => action.payload)
})
