package com.sky.base.serialize.xml.driver;

import java.io.Writer;

import com.sky.base.serialize.xml.XmlConstants;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @Title 可配置格式XppDriver
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-11
 */
public class ConfigurableXppDriver extends XppDriver {

    public enum UnparsedCharacterDataTypeEnum {
        /**
         * 根据条件使用CDATA标签
         */
        CONDITION_CDATA,
        /**
         * 无条件使用CDATA标签
         */
        ALL_CDATA,
        /**
         * 不使用CDATA标签
         */
        NO_CDATA;
    }

    private boolean isCompactFormat;

    private UnparsedCharacterDataTypeEnum unparsedCharacterDataType;

    private ConfigurableXppDriver(NameCoder nameCoder) {
        super(nameCoder);
    }

    public ConfigurableXppDriver(boolean isCompactFormat, UnparsedCharacterDataTypeEnum unparsedCharacterDataType,
            NameCoder nameCoder) {
        this(nameCoder);
        this.isCompactFormat = isCompactFormat;
        this.unparsedCharacterDataType = unparsedCharacterDataType;
    }

    @Override
    public HierarchicalStreamWriter createWriter(Writer out) {
        if (isCompactFormat) {
            return new CompactUnparsedCharacterDataPrintWriter(out, getNameCoder(), unparsedCharacterDataType);
        }
        return new PrettyUnparsedCharacterDataPrintWriter(out, getNameCoder(), unparsedCharacterDataType);
    }

    public boolean isCompactFormat() {
        return isCompactFormat;
    }

    public UnparsedCharacterDataTypeEnum getCharacterDataType() {
        return unparsedCharacterDataType;
    }

    public class PrettyUnparsedCharacterDataPrintWriter extends PrettyPrintWriter {

        private final UnparsedCharacterDataTypeEnum unparsedCharacterDataType;

        public PrettyUnparsedCharacterDataPrintWriter(Writer writer, NameCoder nameCoder,
                UnparsedCharacterDataTypeEnum characterDataType) {
            super(writer, nameCoder);
            this.unparsedCharacterDataType = characterDataType;
        }

        @Override
        protected void writeText(QuickWriter writer, String text) {
            if (UnparsedCharacterDataTypeEnum.CONDITION_CDATA.equals(unparsedCharacterDataType)) {
                if (text.startsWith(XmlConstants.CDATA_PREFIX) && text.endsWith(XmlConstants.CDATA_SUFFIX)) {
                    writer.write(text);
                } else {
                    super.writeText(writer, text);
                }
            } else if (UnparsedCharacterDataTypeEnum.ALL_CDATA.equals(unparsedCharacterDataType)) {
                writer.write(XmlConstants.CDATA_PREFIX + text + XmlConstants.CDATA_SUFFIX);
            } else {
                super.writeText(writer, text);
            }
        }

    }

    public class CompactUnparsedCharacterDataPrintWriter extends PrettyUnparsedCharacterDataPrintWriter {

        public CompactUnparsedCharacterDataPrintWriter(Writer writer, NameCoder nameCoder,
                UnparsedCharacterDataTypeEnum characterDataType) {
            super(writer, nameCoder, characterDataType);
        }

        @Override
        protected void endOfLine() {
            // override parent: don't write anything at end of line
        }
    }
}
