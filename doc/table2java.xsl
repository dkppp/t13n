<xsl:stylesheet version="1.0"
                xmlns:xhtml="http://www.w3.org/1999/xhtml"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        >

    <xsl:output encoding="utf-8" indent="yes"/>
    <xsl:template match="/">
        <xsl:text>Lookup for tables</xsl:text>
        <xsl:apply-templates select="//xhtml:table"/>
    </xsl:template>

    <xsl:template match="xhtml:table">
        <xsl:for-each select="//xhtml:html/xhtml:body/xhtml:table/xhtml:tr">
            <xsl:variable name="from" select="xhtml:td[1]/text()"/>
            <xsl:variable name="m" select="substring-after($from, '(')"/>
            <xsl:variable name="n" select="substring-before($m, ')')"/>
            <xsl:variable name="to" select="xhtml:td[2]/text()"/>
            <xsl:variable name="mm" select="substring-after($to, '(')"/>
            <xsl:variable name="a" select="substring-before($mm, ')')"/>
            <xsl:if test="string-length($from) &gt; 0">
                <xsl:text>{"</xsl:text>
                <xsl:value-of select="$n"/>
                <xsl:text>", "</xsl:text>
                <xsl:value-of select="$a"/>
                <xsl:text>"},
                </xsl:text>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>