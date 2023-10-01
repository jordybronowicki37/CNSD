import "./OverviewPage.scss";
import {AccountItem} from "../components/AccountItem.tsx";
import {useSelector} from "react-redux";
import {StoreTypes} from "../data/DataStore.ts";
import {Holder} from "../data/Types.ts";

export function OverviewPage() {
  const accounts = useSelector<StoreTypes, StoreTypes["accounts"]>(s => s.accounts);
  const holders = useSelector<StoreTypes, StoreTypes["holders"]>(s => s.holders);

  return (
    <div className="overview-page">
      <div className="overview-card">
        <div className="overview-header">
          <h2>Overview</h2>
          <div className="overview-total-container">
            <p>Total saldo:</p>
            <p className="total-saldo">EUR {accounts.map(v => v.saldo).reduce((pv, cv) => pv + cv, 0).toFixed(2)}</p>
          </div>
        </div>
        <div className="accounts-container">
          {accounts.map(v => <AccountItem key={v.id} account={v} title={findHolderById(v.personen[0], holders).naam}/>)}
        </div>
      </div>
    </div>
  );
}

function findHolderById(id: number, list: Holder[]): Holder {
  return list.find(v => v.id === id)!;
}
