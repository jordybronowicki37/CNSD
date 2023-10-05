import {render} from "@testing-library/react";
import {AccountItem} from "./AccountItem.tsx";
import {AccountStatus} from "../data/Types.ts";

test("AccountItem has correct values", () => {
    const account = {
        id: 1,
        iban: "NL99CNSD1234567890",
        saldo: 100,
        status: AccountStatus.NORMAL,
        personen: [1],
    }
    const {queryByText} = render(<AccountItem account={account} title="Title test"/>);

    expect(queryByText(/Title test/i)).toBeTruthy();
    expect(queryByText(/NL99CNSD1234567890/i)).toBeTruthy();
    expect(queryByText(/EUR 100/i)).toBeTruthy();
    expect(queryByText(/Blocked/i)).toBeFalsy();
});

test("AccountItem has correct values for being blocked", () => {
    const account = {
        id: 1,
        iban: "NL99CNSD1234567890",
        saldo: 100,
        status: AccountStatus.BLOCKED,
        personen: [1],
    }
    const {queryByText} = render(<AccountItem account={account} title="Title test"/>);

    expect(queryByText(/Blocked/i)).toBeTruthy();
});
