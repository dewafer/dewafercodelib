select
	user_cons_columns.table_name
	,user_cons_columns.column_name
from
	user_constraints
	,user_cons_columns
where
	user_constraints.r_constraint_name = 'RSCPTYCACACNTPK' /* r_constraint_name */
	and user_constraints.constraint_type = 'R' /* constraint_type */
	and user_cons_columns.constraint_name = user_constraints.constraint_name