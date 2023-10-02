import {Store} from "../DataStore.ts";
import {accountsSetAction} from "../reducers/AccountReducer.ts";

export async function GetAccounts() {
  const user = Store.getState().user;
  if (user === null) return;
  const response = await fetch(`/rekeningen?user=${user.id}`);
  const json = await response.json();
  Store.dispatch(accountsSetAction(json));
}
