//
// Copyright 2010 GOT5 (Gang Of Tapestry 5)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// 	http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.internal.services.DocumentLinker;

@Deprecated
public class DocumentLinkerInterceptor implements DocumentLinker
{
    private final DocumentLinker delegate;

    public DocumentLinkerInterceptor(DocumentLinker delegate)
    {
        this.delegate = delegate;
    }

    public void addScript(String script)
    {
        this.delegate.addScript(script);
    }

    public void addScriptLink(String scriptURL)
    {

        this.delegate.addScriptLink(scriptURL);
    }

    public void addStylesheetLink(String styleURL, String media)
    {
        this.delegate.addStylesheetLink(styleURL, media);
    }

}
