import {Store} from "../DataStore.ts";
import {Holder} from "../Types.ts";
import {holdersSetAction} from "../reducers/HolderReducer.ts";

export async function getHolders() {
  const user = Store.getState().user;
  if (user === null) return;

  const response = await fetch(`/api/persoon`);
  if (!response.ok) throw new Error("Holders fetch failed");
  const json = await response.json() as Holder[];
  Store.dispatch(holdersSetAction(json));
}
