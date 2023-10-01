import {createAction, createReducer} from "@reduxjs/toolkit";
import {Holder} from "../Types.ts";

// TODO remove initial data
const holders: Holder[] = [
  {
    id: 1,
    bsn: "123456789",
    naam: "Holder #1",
    rekeningen: [1, 2]
  },
  {
    id: 2,
    bsn: "abcdefghi",
    naam: "Test name #2",
    rekeningen: [2]
  }
]

export const holderAddAction = createAction<Holder>("holder/add");
export const holdersSetAction = createAction<Holder[]>("holders/set");

export const holderReducer = createReducer<Holder[]>(holders, builder => {
  builder
    .addCase(holderAddAction, (state, action) => [...state, action.payload])
    .addCase(holdersSetAction, (_, action) => [...action.payload])
})
