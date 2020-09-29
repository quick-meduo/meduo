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

package org.quick.meduo.contacts;

import lombok.Data;

/**
 * Used to send Audit Content
 */
@Data
public class AuditContract {
    private String module;    //the application module
    private String who;    //access user, format as name-id, unique name, plus email if available
    private String when;   //access time
    private String where;  //ip address
    private String what;   //details
    private String target; //operate target
    private String type;   //trace, info, debug, warn, error
    private String result; //success or reject
}