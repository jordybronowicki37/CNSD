import {Store} from "../DataStore.ts";
import {accountsSetAction} from "../reducers/AccountReducer.ts";

export async function GetAccounts() {
  const response = await fetch(`/persoon`);
  const json = await response.json();
  Store.dispatch(accountsSetAction(json));
}
