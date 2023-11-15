import "./OverviewPage.scss";
import {NoteItem} from "../components/NoteItem.tsx";

export function OverviewPage() {
  return (
    <div className="overview-page">
      <div className="notes">
        <NoteItem note={{body: "Do your homework", owner: "Jordy", created: new Date()}}/>
      </div>
    </div>
  )
}