import "./OverviewPage.scss";
import {Account, AccountStatus} from "../data/Types.ts";
import {AccountItem} from "../components/AccountItem.tsx";

export function OverviewPage() {
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
          {accounts.map(v => <AccountItem key={v.id} account={v} title="JR Test"/>)}
        </div>
      </div>
    </div>
  );
}