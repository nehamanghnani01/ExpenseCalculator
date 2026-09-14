import { useEffect, useState } from "react";
import { AppBar, Toolbar, Typography, Container, Box, Button } from "@mui/material";
import AccountBalanceWalletIcon from "@mui/icons-material/AccountBalanceWallet";
import AddIcon from "@mui/icons-material/Add";
import CategoryIcon from "@mui/icons-material/Category";
import ReceiptLongIcon from "@mui/icons-material/ReceiptLong";
import api from "./api/api";
import TransactionTable from "./components/TransactionTable";
import AddTransactionPage from "./components/AddTransactionPage";
import CategoriesTable from "./components/CategoriesTable";
import CategoryFormDialog from "./components/CategoryFormDialog";

function App() {

  const [transactions, setTransactions] = useState([]);
  const [categories, setCategories] = useState([]);
  const [view, setView] = useState("table");
  const [addingCategory, setAddingCategory] = useState(false);

  const refreshTransactions = () => {
    api.get("/transactions")
      .then(res => setTransactions(res.data))
      .catch(err => console.log(err));
  };

  const refreshCategories = () => {
    api.get("/categories")
      .then(res => setCategories(res.data))
      .catch(err => console.log(err));
  };

  useEffect(() => {
    refreshTransactions();
    refreshCategories();
  }, []);

  const handleTransactionAdded = () => {
    refreshTransactions();
    setView("table");
  };

  return (
    <Box sx={{ minHeight: "100vh", bgcolor: "background.default" }}>

      <AppBar position="static" color="primary" enableColorOnDark>
        <Toolbar>
          <AccountBalanceWalletIcon sx={{ mr: 1.5 }} />
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Finance Tracker
          </Typography>

          {view === "categories" ? (
            <Button
              color="inherit"
              variant="outlined"
              startIcon={<ReceiptLongIcon />}
              onClick={() => setView("table")}
            >
              Transactions
            </Button>
          ) : (
            <Button
              color="inherit"
              variant="outlined"
              startIcon={<CategoryIcon />}
              onClick={() => setView("categories")}
            >
              Categories
            </Button>
          )}
        </Toolbar>
      </AppBar>

      <Container maxWidth="lg" sx={{ py: 5 }}>

        {view === "table" && (
          <>
            <Box
              sx={{
                mb: 4,
                display: "flex",
                justifyContent: "space-between",
                alignItems: "flex-start",
                flexWrap: "wrap",
                gap: 2
              }}
            >
              <Box>
                <Typography variant="h4" component="h1" gutterBottom>
                  Transactions
                </Typography>
                <Typography variant="body1" color="text.secondary">
                  A record of your income and expenses.
                </Typography>
              </Box>

              <Button
                variant="contained"
                startIcon={<AddIcon />}
                onClick={() => setView("add")}
              >
                Add Transaction
              </Button>
            </Box>

            <TransactionTable
              transactions={transactions}
              categories={categories}
              onTransactionUpdated={refreshTransactions}
            />
          </>
        )}

        {view === "add" && (
          <AddTransactionPage
            categories={categories}
            onSave={handleTransactionAdded}
            onCancel={() => setView("table")}
          />
        )}

        {view === "categories" && (
          <>
            <Box
              sx={{
                mb: 4,
                display: "flex",
                justifyContent: "space-between",
                alignItems: "flex-start",
                flexWrap: "wrap",
                gap: 2
              }}
            >
              <Box>
                <Typography variant="h4" component="h1" gutterBottom>
                  Categories
                </Typography>
                <Typography variant="body1" color="text.secondary">
                  Income and expense categories used to classify transactions.
                </Typography>
              </Box>

              <Button
                variant="contained"
                startIcon={<AddIcon />}
                onClick={() => setAddingCategory(true)}
              >
                Add Category
              </Button>
            </Box>

            <CategoriesTable categories={categories} onCategoriesUpdated={refreshCategories} />

            <CategoryFormDialog
              open={addingCategory}
              category={null}
              onClose={() => setAddingCategory(false)}
              onSaved={refreshCategories}
            />
          </>
        )}

      </Container>

    </Box>
  );
}

export default App;
