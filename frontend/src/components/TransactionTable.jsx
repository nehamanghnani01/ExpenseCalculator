import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";


function TransactionTable({ transactions }) {

  const handleEditTransaction = (transaction) => {
    // Call edit API with transaction data
    fetch(`/api/transactions/${transaction.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(transaction)
    })
      .then((response) => response.json())
      .then((data) => console.log("Transaction updated:", data))
      .catch((error) => console.error("Error updating transaction:", error));
  };

  return (
    <TableContainer 
      component={Paper}
      sx={{ 
        boxShadow: "0 8px 16px rgba(0, 0, 0, 0.15)",
        backgroundColor: "rgba(255, 255, 255, 0.85)"
      }}
    >

      <Table>

        <TableHead sx={{ backgroundColor: "#1976d2" }}>
          <TableRow>

            <TableCell sx={{ color: "white", fontWeight: "bold" }}>Description</TableCell>
            <TableCell sx={{ color: "white", fontWeight: "bold" }}>Category</TableCell>
            <TableCell sx={{ color: "white", fontWeight: "bold" }}>Type</TableCell>
            <TableCell sx={{ color: "white", fontWeight: "bold" }}>Amount</TableCell>
            <TableCell sx={{ color: "white", fontWeight: "bold" }}>Date</TableCell>
            <TableCell sx={{ color: "white", fontWeight: "bold" }}>Actions</TableCell>

          </TableRow>
        </TableHead>


        <TableBody>

          {transactions.map((transaction) => (

            <TableRow key={transaction.id}>

              <TableCell>
                {transaction.description}
              </TableCell>


              <TableCell>
                {transaction.category.name}
              </TableCell>


              <TableCell>
                {transaction.category.type}
              </TableCell>


              <TableCell>
                ${transaction.amount}
              </TableCell>


              <TableCell>
                {transaction.date}
              </TableCell>

              <TableCell>
                <IconButton size="small" color="primary" onClick={() => handleEditTransaction(transaction)}>
                  <EditIcon />
                </IconButton>
              </TableCell>

              <TableCell>
                <IconButton size="small" color="primary">
                  <DeleteIcon />
                </IconButton>
              </TableCell>

            </TableRow>

          ))}

        </TableBody>

      </Table>

    </TableContainer>
  );
}

export default TransactionTable;