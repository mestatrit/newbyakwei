package halo.dataserver.sqlbuilder;

import halo.dataserver.input.InputSqlInfo;

public interface SqlBuilder {

	SqlInfo build(InputSqlInfo inputSqlInfo);
}