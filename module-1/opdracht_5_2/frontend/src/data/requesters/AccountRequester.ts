import {Store} from "../DataStore.ts";
import {accountsSetAction} from "../reducers/AccountReducer.ts";
import {Account} from "../Types.ts";

export async function getAccounts() {
  const user = Store.getState().user;
  if (user === null) return;

  const response = await fetch(`/api/rekening?persoonId=${user.id}`);
  if (!response.ok) throw new Error("Accounts fetch failed");
  const json = await response.json() as Account[];
  Store.dispatch(accountsSetAction(json));
}
