import { useEffect, useState } from "react";
import api from "./api/api";
import TransactionTable from "./components/TransactionTable";

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

      <TransactionTable transactions={transactions} />
    </div>
  );
}

export default App;