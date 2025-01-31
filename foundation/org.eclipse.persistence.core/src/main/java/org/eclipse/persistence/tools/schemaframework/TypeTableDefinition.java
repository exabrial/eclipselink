/*
 * Copyright (c) 1998, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

// Contributors:
//     Oracle - initial API and implementation from Oracle TopLink
package org.eclipse.persistence.tools.schemaframework;

import org.eclipse.persistence.exceptions.ValidationException;
import org.eclipse.persistence.internal.sessions.AbstractSession;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * <b>Purpose</b>: Allow for tabels of Oracle 8 object-relational user defined type to be created.
 * </p>
 */
public class TypeTableDefinition extends TableDefinition {

    /** The name of the type that this table is of. */
    protected String typeName;
    protected String additional = "";

    public TypeTableDefinition() {
        super();
        this.typeName = "";
    }

    /**
     * INTERNAL:
     * Return the create table statement.
     */
    @Override
    public Writer buildCreationWriter(AbstractSession session, Writer writer) {
        try {
            writer.write("CREATE TABLE " + getFullName() + " OF " + getTypeName() + " (");
            List<String> keyFields = getPrimaryKeyFieldNames();
            if ((!keyFields.isEmpty()) && session.getPlatform().supportsPrimaryKeyConstraint()) {
                writer.write("PRIMARY KEY (");
                for (Iterator<String> iterator = keyFields.iterator(); iterator.hasNext();) {
                    writer.write(iterator.next());
                    if (iterator.hasNext()) {
                        writer.write(", ");
                    }
                }
                writer.write(")");
            }
            writer.write(")");
            writer.write(additional);
        } catch (IOException ioException) {
            throw ValidationException.fileError(ioException);
        }
        return writer;
    }

    /**
     * PUBLIC:
     * The name of the type that this table is of.
     */
    public String getAdditonal() {
        return additional;
    }

    /**
     * PUBLIC:
     * The name of the type that this table is of.
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * PUBLIC:
     * The name of the type that this table is of.
     */
    public void setAdditional(String additional) {
        this.additional = additional;
    }

    /**
     * PUBLIC:
     * The name of the type that this table is of.
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
