import {createAction, createReducer} from "@reduxjs/toolkit";
import {Account, AccountStatus} from "../Types.ts";

// TODO remove initial data
const accounts: Account[] = [
  {
    id: 1,
    iban: "NL99CNSD1234567890",
    saldo: 546.95,
    status: AccountStatus.NORMAL,
    personen: [1]
  },
  {
    id: 2,
    iban: "NL99CNSD0987654321",
    saldo: -96.42,
    status: AccountStatus.BLOCKED,
    personen: [2, 1]
  }
]

export const accountAddAction = createAction<Account>("account/add");
export const accountsSetAction = createAction<Account[]>("accounts/set");

export const accountReducer = createReducer<Account[]>(accounts, builder => {
  builder
    .addCase(accountAddAction, (state, action) => [...state, action.payload])
    .addCase(accountsSetAction, (_, action) => [...action.payload])
})
