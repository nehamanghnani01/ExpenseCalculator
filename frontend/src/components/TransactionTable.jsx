import { useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
  Chip,
  Stack,
  Typography
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import EditTransactionDialog from "./EditTransactionDialog";
import DeleteConfirmationDialog from "./DeleteConfirmationDialog";

const currencyFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD"
});

function TransactionTable({ transactions, categories, onTransactionUpdated }) {

  const [editingTransaction, setEditingTransaction] = useState(null);
  const [deletingTransaction, setDeletingTransaction] = useState(null);

  return (
    <TableContainer
      component={Paper}
      variant="outlined"
      sx={{ borderRadius: 2.5 }}
    >

      <Table>

        <TableHead>
          <TableRow sx={{ bgcolor: "primary.main" }}>

            <TableCell sx={{ color: "primary.contrastText", fontWeight: 600 }}>Description</TableCell>
            <TableCell sx={{ color: "primary.contrastText", fontWeight: 600 }}>Category</TableCell>
            <TableCell sx={{ color: "primary.contrastText", fontWeight: 600 }}>Type</TableCell>
            <TableCell align="right" sx={{ color: "primary.contrastText", fontWeight: 600 }}>Amount</TableCell>
            <TableCell sx={{ color: "primary.contrastText", fontWeight: 600 }}>Date</TableCell>
            <TableCell align="center" sx={{ color: "primary.contrastText", fontWeight: 600 }}>Actions</TableCell>

          </TableRow>
        </TableHead>


        <TableBody>

          {transactions.length === 0 && (
            <TableRow>
              <TableCell colSpan={6} align="center" sx={{ py: 6 }}>
                <Typography color="text.secondary">
                  No transactions yet.
                </Typography>
              </TableCell>
            </TableRow>
          )}

          {transactions.map((transaction) => {
            const isIncome = transaction.category.type === "INCOME";

            return (
              <TableRow
                key={transaction.id}
                hover
                sx={{ "&:last-child td": { borderBottom: 0 } }}
              >

                <TableCell>
                  {transaction.description}
                </TableCell>

                <TableCell>
                  {transaction.category.name}
                </TableCell>

                <TableCell>
                  <Chip
                    label={transaction.category.type}
                    size="small"
                    color={isIncome ? "success" : "default"}
                    variant={isIncome ? "filled" : "outlined"}
                  />
                </TableCell>

                <TableCell
                  align="right"
                  sx={{
                    fontWeight: 600,
                    color: isIncome ? "success.main" : "text.primary"
                  }}
                >
                  {isIncome ? "+" : "-"}{currencyFormatter.format(transaction.amount)}
                </TableCell>

                <TableCell>
                  {transaction.date}
                </TableCell>

                <TableCell align="center">
                  <Stack direction="row" spacing={0.5} justifyContent="center">
                    <IconButton size="small" color="primary" onClick={() => setEditingTransaction(transaction)}>
                      <EditIcon fontSize="small" />
                    </IconButton>
                    <IconButton size="small" color="error" onClick={() => setDeletingTransaction(transaction)}>
                      <DeleteIcon fontSize="small" />
                    </IconButton>
                  </Stack>
                </TableCell>

              </TableRow>
            );
          })}

        </TableBody>

      </Table>

      <EditTransactionDialog
        open={editingTransaction !== null}
        transaction={editingTransaction}
        categories={categories}
        onClose={() => setEditingTransaction(null)}
        onUpdated={onTransactionUpdated}
      />

      <DeleteConfirmationDialog
        open={deletingTransaction !== null}
        transaction={deletingTransaction}
        onClose={() => setDeletingTransaction(null)}
        onDeleted={onTransactionUpdated}
      />

    </TableContainer>
  );
}

export default TransactionTable;
