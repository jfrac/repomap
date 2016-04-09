package tfg.repomap.dao.file;

import java.io.File;

public class FileMappingDAOStub
extends FileMappingDAO
{
	public FileMappingDAOStub() {
		BASE_PATH = BASE_PATH + "tests/";
		File folder = new File(BASE_PATH);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}
}
