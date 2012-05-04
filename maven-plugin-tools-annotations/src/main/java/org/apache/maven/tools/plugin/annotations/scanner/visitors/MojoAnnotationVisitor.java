package org.apache.maven.tools.plugin.annotations.scanner.visitors;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.codehaus.plexus.logging.Logger;
import org.objectweb.asm.AnnotationVisitor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Olivier Lamy
 */
public class MojoAnnotationVisitor
    implements AnnotationVisitor
{
    private Logger logger;

    private String annotationClassName;

    private Map<String, Object> annotationValues = new HashMap<String, Object>();

    MojoAnnotationVisitor( Logger logger, String annotationClassName )
    {
        this.logger = logger;
        this.annotationClassName = annotationClassName;
    }

    public Map<String, Object> getAnnotationValues()
    {
        return annotationValues;
    }

    public void visit( String name, Object value )
    {
        annotationValues.put( name, value );
        logger.debug( "MojoAnnotationVisitor#visit:" + name + ":" + value );
    }

    public void visitEnum( String name, String desc, String value )
    {
        annotationValues.put( name, value );
        logger.debug( "MojoAnnotationVisitor#visitEnum:" + name + ":" + desc + ":" + value );
    }

    public AnnotationVisitor visitAnnotation( String name, String desc )
    {
        logger.debug( "MojoAnnotationVisitor#visitAnnotation:" + name + ":" + desc );
        return new MojoAnnotationVisitor( logger, this.annotationClassName );
    }

    public AnnotationVisitor visitArray( String s )
    {
        logger.debug( "MojoAnnotationVisitor#visitArray" );
        return new MojoAnnotationVisitor( logger, this.annotationClassName );
    }

    public void visitEnd()
    {
        logger.debug( "MojoAnnotationVisitor#visitEnd" );
    }

    public String getAnnotationClassName()
    {
        return annotationClassName;
    }
}
