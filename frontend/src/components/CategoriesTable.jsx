import { useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Chip,
  Typography,
  IconButton,
  Stack
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import CategoryFormDialog from "./CategoryFormDialog";
import DeleteCategoryDialog from "./DeleteCategoryDialog";

function CategoriesTable({ categories, onCategoriesUpdated }) {

  const [editingCategory, setEditingCategory] = useState(null);
  const [deletingCategory, setDeletingCategory] = useState(null);

  return (
    <TableContainer
      component={Paper}
      variant="outlined"
      sx={{ borderRadius: 2.5 }}
    >
      <Table>

        <TableHead>
          <TableRow sx={{ bgcolor: "primary.main" }}>
            <TableCell sx={{ color: "primary.contrastText", fontWeight: 600 }}>Name</TableCell>
            <TableCell sx={{ color: "primary.contrastText", fontWeight: 600 }}>Type</TableCell>
            <TableCell align="center" sx={{ color: "primary.contrastText", fontWeight: 600 }}>Actions</TableCell>
          </TableRow>
        </TableHead>

        <TableBody>

          {categories.length === 0 && (
            <TableRow>
              <TableCell colSpan={3} align="center" sx={{ py: 6 }}>
                <Typography color="text.secondary">
                  No categories yet.
                </Typography>
              </TableCell>
            </TableRow>
          )}

          {categories.map((category) => {
            const isIncome = category.type === "INCOME";

            return (
              <TableRow key={category.id} hover>
                <TableCell>{category.name}</TableCell>
                <TableCell>
                  <Chip
                    label={category.type}
                    size="small"
                    color={isIncome ? "success" : "default"}
                    variant={isIncome ? "filled" : "outlined"}
                  />
                </TableCell>
                <TableCell align="center">
                  <Stack direction="row" spacing={0.5} justifyContent="center">
                    <IconButton size="small" color="primary" onClick={() => setEditingCategory(category)}>
                      <EditIcon fontSize="small" />
                    </IconButton>
                    <IconButton size="small" color="error" onClick={() => setDeletingCategory(category)}>
                      <DeleteIcon fontSize="small" />
                    </IconButton>
                  </Stack>
                </TableCell>
              </TableRow>
            );
          })}

        </TableBody>

      </Table>

      <CategoryFormDialog
        open={editingCategory !== null}
        category={editingCategory}
        onClose={() => setEditingCategory(null)}
        onSaved={onCategoriesUpdated}
      />

      <DeleteCategoryDialog
        open={deletingCategory !== null}
        category={deletingCategory}
        onClose={() => setDeletingCategory(null)}
        onDeleted={onCategoriesUpdated}
      />

    </TableContainer>
  );
}

export default CategoriesTable;
