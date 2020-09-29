/*
 *
 *  * Copyright (c) 2019.  the original author or authors.
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

import org.quick.meduo.bus.nats.BusAdaptor;
import org.quick.meduo.contacts.AuditContract;
import org.quick.meduo.core.audit.IAuditProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuditProviderImpl implements IAuditProvider {
    @Autowired
    BusAdaptor busAdaptor;

    @Override
    public void save(String type, String uid, String uname, String where, String target, String what, String result) {
        AuditContract contract = new AuditContract();
        contract.setModule("meduo-admin");
        contract.setWho(uid+"[ "+uname+" ]");
        contract.setWhat(what);
        contract.setTarget(target);
        contract.setWhere(where);
        contract.setType(type);
        contract.setResult(result);
        contract.setWhen(new Date().toString());

        busAdaptor.publish("audit.log",contract);
    }
}
