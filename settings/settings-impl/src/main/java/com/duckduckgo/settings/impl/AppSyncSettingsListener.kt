/*
 * Copyright (c) 2023 DuckDuckGo
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

package com.duckduckgo.settings.impl

import com.duckduckgo.di.scopes.*
import com.duckduckgo.settings.api.*
import com.squareup.anvil.annotations.*
import dagger.*
import javax.inject.Inject
import timber.log.*

@SingleInstanceIn(AppScope::class)
@ContributesBinding(AppScope::class)
class AppSyncSettingsListener @Inject constructor(
    private val syncMetadataDao: SettingsSyncMetadataDao,
) : SyncSettingsListener {
    override fun onSettingChanged(settingKey: String) {
        Timber.i("Sync-Settings: onSettingChanged($settingKey)")
        val entity = SettingsSyncMetadataEntity(
            key = settingKey,
            modified_at = SyncDateProvider.now(),
            deleted_at = null,
        )
        syncMetadataDao.addOrUpdate(entity)
    }
}