package com.jarntang.genertor.core.gen;

import com.jarntang.genertor.core.model.CodeInfo;
import com.jarntang.genertor.core.model.Context;
import com.jarntang.genertor.core.model.TableInfo;

/**
 * generator interface.
 *
 * @author qinmu
 * @since 2017/07/22 23:33
 */
public interface Generator {

  CodeInfo generate(Context context, TableInfo tableInfo);

}
