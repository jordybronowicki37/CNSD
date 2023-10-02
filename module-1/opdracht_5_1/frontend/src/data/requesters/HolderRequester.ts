import {Store} from "../DataStore.ts";
import {Holder} from "../Types.ts";
import {holdersSetAction} from "../reducers/HolderReducer.ts";

// TODO remove initial data
const holders: Holder[] = [
  {
    id: 1,
    bsn: "123456789",
    naam: "Holder #1"
  },
  {
    id: 2,
    bsn: "abcdefghi",
    naam: "Test name #2"
  }
]

export async function GetHolders() {
  const user = Store.getState().user;
  if (user === null) return;

  // TODO Use api
  // const response = await fetch(`/persoon`);
  // const json = await response.json();
  await new Promise(resolve => setTimeout(resolve, 800));
  Store.dispatch(holdersSetAction(holders));
}
