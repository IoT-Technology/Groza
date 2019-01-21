/**
 * Copyright © 2016-2018 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sanshengshui.server.common.msg.core;

import com.sanshengshui.server.common.msg.kv.AttributesKVMsg;
import com.sanshengshui.server.common.msg.session.SessionMsgType;
import com.sanshengshui.server.common.msg.session.ToDeviceMsg;
import lombok.ToString;


@ToString
public class AttributesUpdateNotification implements ToDeviceMsg {

    private static final long serialVersionUID = 1L;

    private AttributesKVMsg data;

    public AttributesUpdateNotification(AttributesKVMsg data) {
        this.data = data;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    public SessionMsgType getSessionMsgType() {
        return SessionMsgType.ATTRIBUTES_UPDATE_NOTIFICATION;
    }

    public AttributesKVMsg getData() {
        return data;
    }
}
