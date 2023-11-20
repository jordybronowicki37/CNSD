import {createAction, createReducer} from "@reduxjs/toolkit";

export const currentUserSetAction = createAction<string>("user/set");

const user = localStorage.getItem("user") ?? "";

export const currentUserReducer = createReducer<string>(user, builder => {
  builder.addCase(currentUserSetAction, (_, action) => {
    localStorage.setItem("user", action.payload);
    return action.payload;
  })
})
