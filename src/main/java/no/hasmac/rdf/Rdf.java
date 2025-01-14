/*
 * Copyright 2020 APICATALOG and HASMAC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * SPDX-License-Identifier: Apache-2.0
 */

package no.hasmac.rdf;

import no.hasmac.jsonld.StringUtils;
import no.hasmac.jsonld.http.media.MediaType;
import no.hasmac.jsonld.lang.BlankNode;
import no.hasmac.jsonld.uri.UriUtils;
import no.hasmac.rdf.io.RdfReader;
import no.hasmac.rdf.io.RdfWriter;
import no.hasmac.rdf.io.error.UnsupportedContentException;
import no.hasmac.rdf.lang.XsdConstants;
import no.hasmac.rdf.spi.RdfProvider;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;

public final class Rdf {

    private Rdf() {
    }

    public static RdfGraph createGraph() {
        return RdfProvider.provider().createGraph();
    }

    public static Collection<MediaType> canRead() {
        return RdfProvider.provider().canRead();
    }

    public static RdfReader createReader(final MediaType contentType, Reader reader) throws UnsupportedContentException {

        if (reader == null || contentType == null) {
            throw new IllegalArgumentException();
        }

        return RdfProvider.provider().createReader(contentType, reader);
    }

    public static RdfReader createReader(final MediaType contentType, final InputStream is) throws UnsupportedContentException {

        if (is == null || contentType == null) {
            throw new IllegalArgumentException();
        }

        return createReader(contentType, new InputStreamReader(is));
    }

    public static Collection<MediaType> canWrite() {
        return RdfProvider.provider().canWrite();
    }

    public static RdfWriter createWriter(final MediaType contentType, final Writer writer) throws UnsupportedContentException {

        if (writer == null || contentType == null) {
            throw new IllegalArgumentException();
        }

        return RdfProvider.provider().createWriter(contentType, writer);
    }

    public static RdfWriter createWriter(final MediaType contentType, final OutputStream os) throws UnsupportedContentException {

        if (os == null || contentType == null) {
            throw new IllegalArgumentException();
        }

        return createWriter(contentType, new OutputStreamWriter(os));
    }

    public static RdfDataset createDataset() {
        return RdfProvider.provider().createDataset();
    }

    public static RdfTriple createTriple(RdfResource subject, RdfResource predicate, RdfValue object) {

        if (subject == null || predicate == null || object == null) {
            throw new IllegalArgumentException();
        }

        return RdfProvider.provider().createTriple(subject, predicate, object);
    }

    public static RdfNQuad createNQuad(RdfResource subject, RdfResource predicate, RdfValue object, RdfResource graphName) {

        if (subject == null) {
            throw new IllegalArgumentException("Subject cannot be null.");
        }
        if (predicate == null) {
            throw new IllegalArgumentException("Predicate cannot be null.");
        }
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null.");
        }

        return RdfProvider.provider().createQuad(subject, predicate, object, graphName);
    }

    public static RdfNQuad createNQuad(RdfTriple triple, RdfResource graphName) {

        if (triple == null) {
            throw new IllegalArgumentException("Triple cannot be null.");
        }

        return RdfProvider.provider().createQuad(triple.getSubject(), triple.getPredicate(), triple.getObject(), graphName);
    }

    public static RdfValue createValue(String value) {

        if (value == null) {
            throw new IllegalArgumentException();
        }

        if (BlankNode.isWellFormed(value)) {
            return RdfProvider.provider().createBlankNode(value);
        }

        if (UriUtils.isAbsoluteUri(value, true)) {
            return RdfProvider.provider().createIRI(value);
        }

        return RdfProvider.provider().createTypedLiteral(value, XsdConstants.STRING);
    }

    public static RdfLiteral createString(String lexicalForm) {

        if (lexicalForm == null) {
            throw new IllegalArgumentException();
        }

        return RdfProvider.provider().createTypedLiteral(lexicalForm, XsdConstants.STRING);
    }

    public static RdfLiteral createTypedString(String lexicalForm, String dataType) {

        if (lexicalForm == null) {
            throw new IllegalArgumentException();
        }

        return RdfProvider.provider().createTypedLiteral(lexicalForm, dataType);
    }

    public static RdfLiteral createLangString(String lexicalForm, String langTag) {

        if (lexicalForm == null) {
            throw new IllegalArgumentException();
        }

        return RdfProvider.provider().createLangString(lexicalForm, langTag);
    }

    /**
     * Create a new {@link RdfResource}.
     *
     * @param resource is an absolute IRI or blank node identifier
     * @return RDF resource
     * @throws IllegalArgumentException if the resource is not an absolute IRI or blank node identifier
     */
    public static RdfResource createResource(String resource) {

        if (resource == null) {
            throw new IllegalArgumentException("The resource value cannot be null.");
        }

        if (BlankNode.isWellFormed(resource)) {
            return RdfProvider.provider().createBlankNode(resource);
        }

        if (UriUtils.isAbsoluteUri(resource, true)) {
            return RdfProvider.provider().createIRI(resource);
        }

        throw new IllegalArgumentException("The resource must be an absolute IRI or blank node identifier, but was [" + resource + "].");
    }

    public static RdfResource createBlankNode(final String value) {

        if (value == null || StringUtils.isBlank(value)) {
            throw new IllegalArgumentException();
        }

        return RdfProvider.provider().createBlankNode(value);
    }

    public static RdfResource createIRI(final String value) {

        if (value == null || StringUtils.isBlank(value)) {
            throw new IllegalArgumentException();
        }

        return RdfProvider.provider().createIRI(value);
    }

    public static RdfValueFactory<RdfTriple, RdfNQuad, RdfResource, RdfResource, RdfResource, RdfLiteral, RdfValue> createValueFactory() {
        return RdfProvider.provider();
    }
}
