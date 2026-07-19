import { useEffect, useState } from "react";
import api from "./api/api";

function App() {

  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    api.get("/transactions")
      .then(res => setTransactions(res.data))
      .catch(err => console.log(err));
  }, []);

  return (
    <div>
      <h1>Finance Tracker</h1>

      {transactions.map(t => (
        <div key={t.id}>
          {t.description} - ${t.amount}
        </div>
      ))}
    </div>
  );
}

export default App;