/*
 *
 *  * Copyright (c) 2020.  the original author or authors.
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.quick.meduo.admin.audit;

import org.quick.meduo.core.audit.IAuditProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditHelper {
    private static IAuditProvider auditProvider = null;

    public AuditHelper(IAuditProvider helper){
        AuditHelper.auditProvider = helper;
    }

    public static void save(String type, String uid, String uname, String where, String target, String what, String result){
        if(AuditHelper.auditProvider != null){
            AuditHelper.auditProvider.save(type,uid,uname,where,target,what,result);
        }
    }
}
